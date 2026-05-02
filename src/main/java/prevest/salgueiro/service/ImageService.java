package prevest.salgueiro.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@ApplicationScoped
public class ImageService {

    private final Cloudinary cloudinary;

    public ImageService(
            @ConfigProperty(name = "cloudinary.cloud-name") String cloudName,
            @ConfigProperty(name = "cloudinary.api-key") String apiKey,
            @ConfigProperty(name = "cloudinary.api-secret") String apiSecret) {
        
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret,
                "secure", true));
    }

    public String uploadImage(File file) throws IOException {
        // O upload retorna um Map com diversos dados, como a 'secure_url'
        Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        return uploadResult.get("secure_url").toString();
    }
}
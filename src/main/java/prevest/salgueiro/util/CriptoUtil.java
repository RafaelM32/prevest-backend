package prevest.salgueiro.util;

import io.quarkus.elytron.security.common.BcryptUtil;

public class CriptoUtil {
				
				public static String criptografarSenha(String senha){
								return BcryptUtil.bcryptHash(senha);
				}
				
}
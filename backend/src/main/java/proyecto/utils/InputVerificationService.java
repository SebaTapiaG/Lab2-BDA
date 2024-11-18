package proyecto.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncriptacionUtil {

    public static String encriptar(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(texto.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar el texto", e);
        }
    }

    public static boolean validarContrasena(String contrasena, String contrasenaEncriptada) {
        return encriptar(contrasena).equals(contrasenaEncriptada);
    }

    public static String desencriptar(String contrasenaEncriptada) {
        return contrasenaEncriptada;
    }
}

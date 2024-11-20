package proyecto.utils;

import org.springframework.stereotype.Repository;

@Repository
public class InputVerificationService{
    public static Boolean validateInput(String input) {
        return !input.contains(";") && !input.contains("--") &&
                !input.contains("`") && !input.contains("'") &&
                !input.contains("\"") && !input.contains("\\") &&
                !input.contains("/");
    }
}


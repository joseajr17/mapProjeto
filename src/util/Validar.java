package util;

import java.util.regex.Pattern;

public class Validar {

	public static boolean validarCpf(String cpf) {
		if(cpf.length() > 14){
			return false;
		}
		if(cpf.length() == 14){
			if(cpf.charAt(3) != '.' || cpf.charAt(7) != '.' || cpf.charAt(11) != '-'){
				return false;
			}
		}
		cpf = cpf.replaceAll("[^0-9]", "");
        
        // Verifica se o CPF possui 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }
        
        // Verifica se todos os dígitos são iguais (CPF inválido)
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }
        
        // Calcula o primeiro dígito verificador
        int digito1 = calcularDigitoVerificador(cpf.substring(0, 9), 10);
        if (digito1 != Character.getNumericValue(cpf.charAt(9))) {
            return false;
        }
        
        // Calcula o segundo dígito verificador
        int digito2 = calcularDigitoVerificador(cpf.substring(0, 10), 11);
        if (digito2 != Character.getNumericValue(cpf.charAt(10))) {
            return false;
        }
        
        // CPF válido
        return true;
	}

	private static int calcularDigitoVerificador(String cpfParcial, int pesoInicial) {
        int soma = 0;
        for (int i = 0; i < cpfParcial.length(); i++) {
            int digito = Character.getNumericValue(cpfParcial.charAt(i));
            soma += digito * pesoInicial;
            pesoInicial--;
        }
        int resto = soma % 11;
        if (resto < 2) {
            return 0;
        } else {
            return 11 - resto;
        }
    }

    public static boolean validarCpfOuCnpj(String cpfOuCnpj){
        if(cpfOuCnpj.length() > 18){
            return false;
        }

        if(cpfOuCnpj.length() == 18){
            if(cpfOuCnpj.charAt(2) != '.' || cpfOuCnpj.charAt(6) != '.' || cpfOuCnpj.charAt(10) != '/' || cpfOuCnpj.charAt(15) != '-' ){
                return false;
            }
        }

        if(cpfOuCnpj.length() <= 18 && cpfOuCnpj.replaceAll("[^\\d]", "").length() == 11){
            return validarCpf(cpfOuCnpj);
        }

             // Remover caracteres não numéricos
             cpfOuCnpj = cpfOuCnpj.replaceAll("[^\\d]", "");

             // Verificar se o CNPJ possui 14 dígitos
             if (cpfOuCnpj.length() != 14) {
                 return false;
             }
     
             // Verificar se todos os dígitos são iguais (ex: 11111111111111)
             if (cpfOuCnpj.matches("(\\d)\\1*")) {
                 return false;
             }
     
             // Calcular o primeiro dígito verificador
             int soma = 0;
             int peso = 2;
             for (int i = 11; i >= 0; i--) {
                 int digito = Character.getNumericValue(cpfOuCnpj.charAt(i));
                 soma += digito * peso;
                 peso = (peso == 9) ? 2 : peso + 1;
             }
             int resto = soma % 11;
             int digitoVerificador1 = (resto < 2) ? 0 : 11 - resto;
     
             // Calcular o segundo dígito verificador
             soma = 0;
             peso = 2;
             for (int i = 12; i >= 0; i--) {
                 int digito = Character.getNumericValue(cpfOuCnpj.charAt(i));
                 soma += digito * peso;
                 peso = (peso == 9) ? 2 : peso + 1;
             }
             resto = soma % 11;
             int digitoVerificador2 = (resto < 2) ? 0 : 11 - resto;
     
             // Verificar se os dígitos verificadores estão corretos
             return Character.getNumericValue(cpfOuCnpj.charAt(12)) == digitoVerificador1
                     && Character.getNumericValue(cpfOuCnpj.charAt(13)) == digitoVerificador2;

    }

    public static boolean validarEmail(String email){
         String EMAIL = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        Pattern pattern = Pattern.compile(EMAIL);
        return pattern.matcher(email).matches();
    }
	
	public static boolean validarSenha(String senha) {
        // Verificar o comprimento mínimo da senha
        if (senha.length() < 6) {
            return false;
        }
        
        // Verificar a presença de letras e dígitos 
        boolean letra = false, digito = false;
        
        for (char c : senha.toCharArray()) {
            if (Character.isUpperCase(c) || Character.isLowerCase(c)) {
                letra = true;
            } 
            if (Character.isDigit(c)) {
                digito = true;
            }
        }
        
        // Verificar se todos os critérios foram atendidos
        return letra && digito;
    }

}

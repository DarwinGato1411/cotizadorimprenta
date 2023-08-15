/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imp.utilitario;


/**
 *
 * @author gato
 */
public class Utilitario {

    public static class validar {

        public static boolean validadorDeCedula(String cedula) {
            boolean cedulaCorrecta = false;

            try {

                if (cedula.length() == 10) // ConstantesApp.LongitudCedula
                {
                    int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
                    if (tercerDigito < 6) {
// Coeficientes de validación cédula
// El decimo digito se lo considera dígito verificador
                        int[] coefValCedula = {2, 1, 2, 1, 2, 1, 2, 1, 2};
                        int verificador = Integer.parseInt(cedula.substring(9, 10));
                        int suma = 0;
                        int digito = 0;
                        for (int i = 0; i < (cedula.length() - 1); i++) {
                            digito = Integer.parseInt(cedula.substring(i, i + 1)) * coefValCedula[i];
                            suma += ((digito % 10) + (digito / 10));
                        }

                        if ((suma % 10 == 0) && (suma % 10 == verificador)) {
                            cedulaCorrecta = true;
                        } else if ((10 - (suma % 10)) == verificador) {
                            cedulaCorrecta = true;
                        } else {
                            cedulaCorrecta = false;
                        }
                    } else {
                        cedulaCorrecta = false;
                    }
                } else {
                    cedulaCorrecta = false;
                }
            } catch (NumberFormatException nfe) {
                cedulaCorrecta = false;
            } catch (Exception err) {
                System.out.println("Una excepcion ocurrio en el proceso de validadcion");
                cedulaCorrecta = false;
            }

            if (!cedulaCorrecta) {
                System.out.println("La Cédula ingresada es Incorrecta");
            }
            return cedulaCorrecta;
        }
    }

    public static String NumeroFactura(String numero) {
        String nuevoNuemro = "";
        try {

//            BigDecimal numeroFactura = BigDecimal.valueOf(Double.valueOf(numero)).add(BigDecimal.ONE);
//
//            numeroFactura.setScale(0, BigDecimal.ROUND_DOWN);
//            Integer.parseInt(numero);
            Integer numeroFactura = Integer.parseInt(numero) + 1;
            nuevoNuemro = numeroFactura.toString();
//            System.out.println("valor de numero de factura " + numeroFactura);
//            if (Double.valueOf(numero) < 100) {
//                String strNuevoNumero = "00000" + numeroFactura;
//                nuevoNuemro = strNuevoNumero;
//            }
//
//            if (Double.valueOf(numero) >= 100 && Double.valueOf(numero) < 1000) {
//                String strNuevoNumero = "0000" + numeroFactura;
//                nuevoNuemro = strNuevoNumero;
//            }
//
//            if (Double.valueOf(numero) >= 1000 && Double.valueOf(numero) < 10000) {
//                String strNuevoNumero = "000" + numeroFactura;
//                nuevoNuemro = strNuevoNumero;
//            }
//
//
//            if (Double.valueOf(numero) >= 1000 && Double.valueOf(numero) < 10000) {
//                String strNuevoNumero = "00" + numeroFactura;
//                nuevoNuemro = strNuevoNumero;
//            }
//
//            if (Double.valueOf(numero) >= 10000 && Double.valueOf(numero) < 100000) {
//                String strNuevoNumero = "0" + numeroFactura;
//                nuevoNuemro = strNuevoNumero;
//            }
//            if (Double.valueOf(numero) >= 1000000) {
//                String strNuevoNumero = String.valueOf(numeroFactura);
//                nuevoNuemro = strNuevoNumero;
//            }
            return nuevoNuemro;
        } catch (Exception e) {
        }
        return null;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package generdorCongruencialMultiplicativo;
/**
 *
 * @author Luis Roberto Lara y Rafael Martinez 
 */
public class CongruencialMultiplicativo {
    // Función para verificar si un número es primo
    private static boolean esPrimo(long num) {
        if (num <= 1) return false;
        if (num <= 3) return true;
        if (num % 2 == 0 || num % 3 == 0) return false;
        for (long i = 5; i * i <= num; i = i + 6) {
            if (num % i == 0 || num % (i + 2) == 0)
                return false;
        }
        return true;
    }

    // Función para calcular el Máximo Común Divisor (MCD)
    private static long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
    
    private static boolean esprimoPrimitivo(long a, long m) {
        // Si m no es primo, no puede haber una raíz primitiva modulo m en el sentido tradicional para GCM.
        if (!esPrimo(m)) {
            return false;
        }
        
        // El orden de 'a' modulo 'm' debe ser phi(m), que es m-1 si m es primo.
        // Esto implica que a^(m-1) % m == 1, y a^k % m != 1 para 1 <= k < m-1.
        // Para verificar esto, se deben probar los divisores de m-1.
        // Aquí solo se hace una verificación superficial.
        if (Math.pow(a, m - 1) % m != 1) { // Pequeño teorema de Fermat
            return false;
        }
        return true; 
    }


    public static void main(String[] args) {
        // Parámetros para el Generador Congruencial Multiplicativo (GCM)
        long m = 32027; // modulo
        long a = 7; // Multiplicador 
        long x0 = 1; // Semilla

        // --- Validaciones ---
        System.out.println("Realizando validaciones de parametros...");

        // 1. Validar m (m debe ser un número primo)
        if (!esPrimo(m)) {
            System.err.println("ERROR: El modulo 'm' (" + m + ") no es un numero primo. Un GCM con período completo requiere un 'm' primo.");
            return; // Terminar el programa si la validación falla
        }
        System.out.println("Validación de 'm' exitosa: " + m + " es primo.");

        // 2. Validar X0 (X0 debe ser primo relativo con m)
        if (x0 <= 0 || x0 >= m) {
            System.err.println("ERROR: La semilla 'X0' (" + x0 + ") debe estar entre 1 y m-1 para un período completo.");
            return;
        }
        if (gcd(x0, m) != 1) {
            System.err.println("ERROR: La semilla 'X0' (" + x0 + ") no es primo relativo con el modulo 'm' (" + m + ").");
            return;
        }
        System.out.println("Validacion de 'X0' exitosa: " + x0 + " es primo relativo con " + m + ".");

        // 3. Validar a (a debe ser una raíz primitiva módulo m)
        if (!esprimoPrimitivo(a, m)) {
             System.err.println("ERROR: El multiplicador 'a' (" + a + ") no es una raiz primitiva módulo 'm' (" + m + "). El generador no tendrá un periodo completo.");
        } else {
             System.out.println("Validacion de 'a' exitosa: " + a + " es una raiz primitiva modulo " + m + " (verificacion superficial).");
        }
       
        System.out.println("\nValidaciones de parámetros completadas.");
        System.out.println("----------------------------------------\n");

        // Continuación del Generador Congruencial Multiplicativo
        long numToGenerate = m - 1; // Para un m primo, el período es m-1

        System.out.println("Iniciando Generador Congruencial Multiplicativo con los siguientes parametros:");
        System.out.println("m (Módulo): " + m + " (Binario: " + Long.toBinaryString(m) + ")");
        System.out.println("a (Multiplicador): " + a + " (Binario: " + Long.toBinaryString(a) + ")");
        System.out.println("X0 (Semilla): " + x0 + " (Binario: " + Long.toBinaryString(x0) + ")");
        System.out.println("Periodo Completo (Cantidad de numeros a generar): " + numToGenerate);
        System.out.println("\nGenerando " + numToGenerate + " números:");
        System.out.println("----------------------------------------------");
        System.out.printf("%-8s %-15s %s%n", "n", "Xn", "Xn/m");
        System.out.println("----------------------------------------------");

        long ValorGenerado = x0;
        
        for (int i = 1; i <= numToGenerate; i++) { // 'i' es el incremento 'n'
            ValorGenerado = (a * ValorGenerado) % m; // Xn+1 = (a * Xn) (mod m)
            double normalizedValue = (double) ValorGenerado / m; // Calcula Xn/m

            System.out.printf("%-10d %-10d %.5f%n n:",i, ValorGenerado, normalizedValue); // Imprime en consola
        }

        System.out.println("----------------------------------------------");
        System.out.println("\nGeneracion de numeros completada. " + numToGenerate + " numeros generados.");
    }
}

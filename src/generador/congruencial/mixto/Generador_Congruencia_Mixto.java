/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package generador.congruencial.mixto;

/**
 *
 * @author Lara Segura Luis Roberto 23070469 Martínez Constantino Rafael
 * #23070460
 */
public class Generador_Congruencia_Mixto {

    private long a;  // Multiplicador
    private long c;  // Incremento
    private long xo; // Semilla (valor inicial)
    private long m;  // Módulo
    private long estado; // Estado actual del generador

    /**
     * Constructor que inicializa el generador con los parámetros especificados.
     *
     * @param a El multiplicador.
     * @param c El incremento.
     * @param xo La semilla.
     * @param m El módulo.
     * @throws IllegalArgumentException si los parámetros no cumplen las
     * condiciones para período completo.
     */
    public Generador_Congruencia_Mixto(long a, long c, long xo, long m) {
        // Validación de los parámetros para asegurar período completo (condiciones de Hull-Dobell)
        if (!esPeriodoCompleto(a, c, m)) {
            throw new IllegalArgumentException("Los parámetros no cumplen las condiciones para un período completo.");
        }

        this.a = a;
        this.c = c;
        this.xo = xo;
        this.m = m;
        this.estado = xo;
    }

    /**
     * Constructor que inicializa el generador con los parámetros especificados
     * de tipo `int`. Realiza la conversión a `long` antes de inicializar.
     *
     * @param a El multiplicador (int).
     * @param c El incremento (int).
     * @param xo La semilla (int).
     * @param m El módulo (int).
     * @throws IllegalArgumentException si los parámetros no cumplen las
     * condiciones para período completo.
     */
    public Generador_Congruencia_Mixto(int a, int c, int xo, int m) {
        this((long) a, (long) c, (long) xo, (long) m);
    }

    /**
     * Verifica si los parámetros cumplen las condiciones de Hull-Dobell para un
     * período completo.
     *
     * @param a El multiplicador.
     * @param c El incremento.
     * @param m El módulo.
     * @return true si los parámetros aseguran un período completo, false de lo
     * contrario.
     */
    private boolean esPeriodoCompleto(long a, long c, long m) {
        // 1. c y m deben ser coprimos (su MCD debe ser 1).
        if (mcd(c, m) != 1) {
            System.out.println("Error: c y m no son coprimos.");
            return false;
        }

        // 2.  a - 1 debe ser divisible por todos los factores primos de m.
        long tempM = m;
        for (long i = 2; i * i <= tempM; i++) {
            if (tempM % i == 0) {
                if ((a - 1) % i != 0) {
                    System.out.println("Error: a-1 no es divisible por el factor primo " + i + " de m.");
                    return false;
                }
                while (tempM % i == 0) {
                    tempM /= i;
                }
            }
        }
        if (tempM > 1 && (a - 1) % tempM != 0) {
            System.out.println("Error: a-1 no es divisible por el factor primo " + tempM + " de m.");
            return false;
        }

        // 3. Si m es divisible por 4, entonces a - 1 debe ser divisible por 4.
        if (m % 4 == 0 && (a - 1) % 4 != 0) {
            System.out.println("Error: Si m es divisible por 4, a-1 también debe serlo.");
            return false;
        }

        return true;
    }

    /**
     * Calcula el Máximo Común Divisor (MCD)
     * @param a El primer número.
     * @param b El segundo número.
     * @return El MCD de a y b.
     */
    private long mcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    /**
     * Genera el siguiente número pseudoaleatorio en la secuencia.
     *
     * @return El siguiente número pseudoaleatorio en el rango [0, m].
     */
    public long siguiente() {
        estado = (a * estado + c) % m;
        return estado;
    }

    /**
     * Genera un número pseudoaleatorio en el rango [0, 1].
     *
     * @return Un número pseudoaleatorio en el rango [0, 1].
     */
    public double siguienteDouble() {
        return (double) siguiente() / m;
    }
    //obtenemos el m 
    public long getModulo() {
        return this.m;
    }
    
    public static void main(String[] args) {

        long m = 50;
        long a = 21;
        long c = 13;
        long xo = 1;

        try {
            Generador_Congruencia_Mixto generador = new Generador_Congruencia_Mixto(a, c, xo, m);

            System.out.println("Generador Congruencial Lineal Mixto:");
            System.out.println("a = " + a);
            System.out.println("c = " + c);
            System.out.println("xo = " + xo);
            System.out.println("m = " + m);

            System.out.println("\nGenerando m numeros pseudoaleatorios:");
            System.out.println("\nIteracion \t(a* Xn + C)/m");
            
            //Empezamos las iteraciones despues X0
            for (int i = 0; i < m; i++) {
                System.out.println("\t"+ i + "\t " + generador.siguiente() + "\t\t (Double: " + generador.siguienteDouble() + ")");
            }
            
            // Verificar Periodo (opcional, pero útil para validar)
            System.out.println("\nVerificando el periodo...");
            verificarPeriodo(generador);

        } catch (IllegalArgumentException e) {
            System.err.println("Error al inicializar el generador: " + e.getMessage());
        }
    }

    /**
     * Verifica el período del generador. Esto puede ser computacionalmente
     * costoso para valores grandes de `m`.
     *
     * @param generador El generador congruencial mixto a verificar.
     */
    public static void verificarPeriodo(Generador_Congruencia_Mixto generador) {
        long xo = generador.xo;
        long m = generador.getModulo();
        long valorActual = generador.siguiente();
        long contador = 1;
         
        while (valorActual != xo && contador < m) {  // Limitamos a m para evitar bucles infinitos
            valorActual = generador.siguiente();
            contador++;
        }

        if (valorActual == xo) {
            System.out.println("El periodo del generador es: " + contador);
        } else {
            System.out.println("No se pudo determinar el periodo completo.  Podria ser mayor a " + m + ".");
        }

    }

}

package validador;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

class ValidadorContrasena implements Runnable {
    private final String contrasena;

    public ValidadorContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public void run() {
        validarContrasena();
    }

    private void validarContrasena() {
        boolean esValida = esLongitudValida() && tieneCaracterEspecial() && tieneMayusculas() && tieneMinusculas() && tieneNumeros();

        if (esValida) {
            System.out.println("La contraseña \"" + contrasena + "\" es válida.");
        } else {
            System.out.println("La contraseña \"" + contrasena + "\" NO es válida.");
        }
    }

    private boolean esLongitudValida() {
        return contrasena.length() >= 8;
    }

    private boolean tieneCaracterEspecial() {
        return Pattern.compile("[!@#$%^&*()-+]").matcher(contrasena).find();
    }

    private boolean tieneMayusculas() {
        return Pattern.compile("[A-Z]").matcher(contrasena).results().count() >= 2;
    }

    private boolean tieneMinusculas() {
        return Pattern.compile("[a-z]").matcher(contrasena).results().count() >= 3;
    }

    private boolean tieneNumeros() {
        return Pattern.compile("[0-9]").matcher(contrasena).find();
    }
}

public class ValidadorContrasenas {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Thread> hilos = new ArrayList<>();

        System.out.println("Ingrese las contraseñas separadas por coma (por ejemplo: Pass1234!, Abcdef123, etc.):");
        String[] contrasenas = scanner.nextLine().split(",");

        for (String contrasena : contrasenas) {
            Thread hilo = new Thread(new ValidadorContrasena(contrasena.trim()));
            hilos.add(hilo);
            hilo.start();
        }

        for (Thread hilo : hilos) {
            try {
                hilo.join(); // Espera a que todos los hilos terminen
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        scanner.close();
    }
}

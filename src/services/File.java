package src.services;

import java.io.*;

public class File {
    private static final String DIR_PATH = System.getenv("LOCALAPPDATA") + java.io.File.separator + "Vheera" + java.io.File.separator + "settings";
    private static final String FILE_NAME = DIR_PATH + java.io.File.separator + "settings.ini";

    // Legge i valori dal file, restituisce un array di 3 interi [pomodoro, breve, lunga]
    public static int[] readSettings() {
        int[] defaults = {25, 5, 30};
        java.io.File dir = new java.io.File(DIR_PATH);
        if (!dir.exists()) dir.mkdirs();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line = br.readLine();
            if (line != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    return new int[] {
                        Integer.parseInt(parts[0].trim()),
                        Integer.parseInt(parts[1].trim()),
                        Integer.parseInt(parts[2].trim())
                    };
                }
            }
        } catch (IOException | NumberFormatException e) {
            // Se il file non esiste o è malformato, restituisce i valori di default
        }
        return defaults;
    }

    // Scrive i valori nel file
    public static void writeSettings(int pomodoro, int breve, int lunga) {
        java.io.File dir = new java.io.File(DIR_PATH);
        if (!dir.exists()) dir.mkdirs();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            bw.write(pomodoro + "," + breve + "," + lunga);
        } catch (IOException e) {
            // Gestione errore scrittura (opzionale: mostrare un messaggio)
        }
    }
}

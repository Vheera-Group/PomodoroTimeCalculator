
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.time.Duration;
import java.util.Locale;
import models.PomodoroTime;
import services.File;

public class Main {
    public static void main(String[] args) {
        String lang = Locale.getDefault().getLanguage();

        // Testi in base alla lingua
        String title = lang.equals("it") ? "Calcolatore Tempo Pomodoro" : "Pomodoro Time Calculator";
        String labelText = lang.equals("it") ? "Inserisci il numero di pomodori:" : "Enter number of pomodoros:";
        String pomodoroLabelText = lang.equals("it") ? "Durata pomodoro (minuti):" : "Pomodoro duration (minutes):";
        String restLabelText = lang.equals("it") ? "Pausa breve (minuti):" : "Short break (minutes):";
        String longRestLabelText = lang.equals("it") ? "Pausa lunga (minuti):" : "Long break (minutes):";
        String buttonText = lang.equals("it") ? "Calcola" : "Calculate";
        String invalidInputText = lang.equals("it") ? "Inserisci valori numerici validi!" : "Enter valid numeric values!";
        String totalResultPrefix = lang.equals("it") ? "Tempo totale: " : "Total time: ";
        String resultPrefix = lang.equals("it") ? "Tempo di concentrazione : " : "Focus time: ";
        String totalBreakPrefix = lang.equals("it") ? "Tempo di riposo: " : "Break time: ";
        String hourSuffix = lang.equals("it") ? "h, " : "h, ";
        String minSuffix = lang.equals("it") ? "m" : "m";

        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(250, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(13, 1));

        JLabel label = new JLabel(labelText);
        JTextField textField = new JTextField();

        JLabel pomodoroLabel = new JLabel(pomodoroLabelText);
        int[] settings = File.readSettings();
        JTextField pomodoroField = new JTextField(String.valueOf(settings[0]));

        JLabel restLabel = new JLabel(restLabelText);
        JTextField restField = new JTextField(String.valueOf(settings[1]));

        JLabel longRestLabel = new JLabel(longRestLabelText);
        JTextField longRestField = new JTextField(String.valueOf(settings[2]));

        JButton button = new JButton(buttonText);
        JLabel totalResultLabel = new JLabel("");
        JLabel resultLabel = new JLabel("");
        JLabel totalBreakLabel = new JLabel(""); // Nuovo label per il tempo pause

        button.addActionListener(e -> {
            try {
                int pomodori = Integer.parseInt(textField.getText());
                int pomodoroDuration = Integer.parseInt(pomodoroField.getText());
                int pomodoroRestDuration = Integer.parseInt(restField.getText());
                int pomodoroLongRestDuration = Integer.parseInt(longRestField.getText());
                PomodoroTime pomodoro = new PomodoroTime(pomodoroDuration, pomodoroRestDuration, pomodoroLongRestDuration);
                Duration totalPomodoro = Duration.ofSeconds(0);
                Duration focusPomodoro = Duration.ofSeconds(0);
                Duration totalBreakTime = Duration.ofSeconds(0); // Nuova variabile

                int longRestNumber = pomodori / 4;
                int shortRestNumber = pomodori - longRestNumber;

                if (pomodori % 4 == 0)
                    longRestNumber -= 1;
                else
                    shortRestNumber -= 1;

                totalPomodoro = totalPomodoro.plusMinutes(pomodori * pomodoro.getPomodoro());
                totalPomodoro = totalPomodoro.plusMinutes(shortRestNumber * pomodoro.getRest());
                totalPomodoro = totalPomodoro.plusMinutes(longRestNumber * pomodoro.getLongRest());

                focusPomodoro = focusPomodoro.plusMinutes(pomodori * pomodoro.getPomodoro());
                totalBreakTime = totalBreakTime.plusMinutes(shortRestNumber * pomodoro.getRest());
                totalBreakTime = totalBreakTime.plusMinutes(longRestNumber * pomodoro.getLongRest());

                String totalResult = totalResultPrefix + totalPomodoro.toHours() + hourSuffix +
                        (totalPomodoro.toMinutes() - totalPomodoro.toHours() * 60) + minSuffix;
                String result = resultPrefix + focusPomodoro.toHours() + hourSuffix +
                        (focusPomodoro.toMinutes() - focusPomodoro.toHours() * 60) + minSuffix;
                String totalBreakResult = totalBreakPrefix + totalBreakTime.toHours() + hourSuffix
                        + (totalBreakTime.toMinutes() - totalBreakTime.toHours() * 60) + minSuffix;

                totalResultLabel.setText(totalResult);
                resultLabel.setText(result);
                totalBreakLabel.setText(totalBreakResult);
                File.writeSettings(pomodoroDuration, pomodoroRestDuration, pomodoroLongRestDuration);
            } catch (NumberFormatException ex) {
                totalResultLabel.setText(invalidInputText);
                resultLabel.setText(invalidInputText);
                totalBreakLabel.setText(invalidInputText);
            }
        });

        panel.add(label);
        panel.add(textField);
        panel.add(pomodoroLabel);
        panel.add(pomodoroField);
        panel.add(restLabel);
        panel.add(restField);
        panel.add(longRestLabel);
        panel.add(longRestField);
        panel.add(button);
        panel.add(totalResultLabel);
        panel.add(resultLabel);
        panel.add(totalBreakLabel); // Aggiungi il nuovo label

        frame.add(panel);
        frame.setVisible(true);
    }
}
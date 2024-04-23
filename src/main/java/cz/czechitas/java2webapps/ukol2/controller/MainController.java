package cz.czechitas.java2webapps.ukol2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
public class MainController {
    private final Random random = new Random();

    private static List<String> readAllLines(String resource) throws IOException {
        //Soubory z resources se získávají pomocí classloaderu. Nejprve musíme získat aktuální classloader.
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        //Pomocí metody getResourceAsStream() získáme z classloaderu InpuStream, který čte z příslušného souboru.
        //Následně InputStream převedeme na BufferedRead, který čte text v kódování UTF-8
        try (InputStream inputStream = classLoader.getResourceAsStream(resource);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            //Metoda lines() vrací stream řádků ze souboru. Pomocí kolektoru převedeme Stream<String> na List<String>.
            return reader
                    .lines()
                    .collect(Collectors.toList());
        }
    }

    @GetMapping("/")
    public ModelAndView zobrazCitat() throws IOException {
        int nahodnyIndexCitatu = random.nextInt(readAllLines("citaty.txt").size());
        String nahodnyCitat = readAllLines("citaty.txt").get(nahodnyIndexCitatu);

        List<String> seznamObrazku = List.of("Oz_J_FXKvIs", "82TpEld0_e4", "cM94J4lOSU0", "RLw-UC03Gwc", "j4uuKnN43_M");
        int nahodnyIndexObrazku = random.nextInt(seznamObrazku.size());
        String nahodnyObrazek = seznamObrazku.get(nahodnyIndexObrazku);

        ModelAndView result = new ModelAndView("citat");
        result.addObject("obrazek", nahodnyObrazek);
        result.addObject("text", nahodnyCitat);
        return result;
    }
}

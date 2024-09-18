package com.example.sortingapp2.controller;

import com.example.sortingapp2.model.SortedElement;
import com.example.sortingapp2.repository.SortedElementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SortingController {

    private final SortedElementRepository repository;

    @Autowired
    public SortingController(SortedElementRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String showForm() {
        return "index";
    }

    @PostMapping("/sort")
    public String sortValues(@RequestParam("numbers") String numbers, Model model) {
        int[] numArray = parseNumbers(numbers);

        Arrays.sort(numArray);
        Long sortId = System.currentTimeMillis();
        saveSortedNumbersToDatabase(numArray, sortId);

        String sortedNumbers = formatSortedNumbers(numArray);
        model.addAttribute("sortedNumbers", sortedNumbers);
        model.addAttribute("sortId", sortId);
  
        return "result";
    }

    @GetMapping("/api/sorted/{sortId}")
    public ResponseEntity<List<Integer>> getSortedArray(@PathVariable Long sortId) {
        List<SortedElement> elements = repository.findBySortId(sortId);

        List<Integer> sortedValues = elements.stream()
                .map(SortedElement::getElementValue)
                .toList();

        return ResponseEntity.ok(sortedValues);
    }

    private int[] parseNumbers(String numbers) {
        String[] numStrs = numbers.split(",");
        int[] numArray = new int[numStrs.length];

        try {
            for (int i = 0; i < numStrs.length; i++) {
                numArray[i] = Integer.parseInt(numStrs[i].trim());
            }
        } catch (NumberFormatException e) {
            return null;
        }

        return numArray;
    }

    private void saveSortedNumbersToDatabase(int[] numArray, Long sortId) {
        for (int num : numArray) {
            SortedElement sortedElement = new SortedElement(num, sortId);
            repository.save(sortedElement);
        }
    }

    private String formatSortedNumbers(int[] numArray) {
        return Arrays.stream(numArray)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(", "));
    }
}

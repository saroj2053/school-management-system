package com.sah.bright_future_academy.controller;

import com.sah.bright_future_academy.model.Holiday;
import com.sah.bright_future_academy.repo.HolidaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class HolidaysController {

    @Autowired
    private HolidaysRepository holidaysRepository;

//    @GetMapping("/holidays")
//    public String displayHolidays(@RequestParam(required=false) boolean festival,
//                                  @RequestParam(required=false) boolean federal, Model model) {
//        model.addAttribute("festival", festival);
//        model.addAttribute("federal", federal);
//        List<Holiday> holidays = Arrays.asList(
//                new Holiday("Sep 22 - Oct 1st","Dashain", "Celebration of good over evil", Holiday.Type.FESTIVAL),
//                new Holiday("Oct 18 - Oct 23","Tihar", "Festival of lights, celebrating the goddess Laxmi", Holiday.Type.FESTIVAL),
//                new Holiday("Mar 14","Holi", "Festival of colors, celebrating the arrival of spring", Holiday.Type.FESTIVAL),
//                new Holiday("May 12","Buddha Jayanti", "Celebrates the birth of Lord Buddha", Holiday.Type.FESTIVAL),
//                new Holiday("Oct 27","Chhath", "Festival dedicated to the sun god", Holiday.Type.FESTIVAL),
//                new Holiday("May 28", "Republic Day", "Celebration of the establishment of the republic", Holiday.Type.FEDERAL),
//                new Holiday("April 24", "Democracy Day", "Celebration of the restoration of democracy", Holiday.Type.FEDERAL),
//                new Holiday("Sept 20", "Constitution Day", "Celebration of the adoption of the constitution", Holiday.Type.FEDERAL),
//                new Holiday("March 8", "International Women's Day", "Celebration of women's achievements and rights", Holiday.Type.FEDERAL)
//        );
//
//        Holiday.Type[] types = Holiday.Type.values();
//        for (Holiday.Type type : types) {
//            model.addAttribute(type.toString(),
//                    holidays.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList()));
//        }
//        return "holidays.html";
//    }

    @GetMapping("/holidays/{display}")
    public String holidays(@PathVariable String display, Model model) {
        if(display != null && display.equals("all")) {
            model.addAttribute("festival", true);
            model.addAttribute("federal", true);
        } else if(display != null && display.equals("federal")) {
            model.addAttribute("federal", true);
        } else if(display != null && display.equals("festival")) {
            model.addAttribute("festival", true);
        }

//        List<Holiday> holidays = Arrays.asList(
//                new Holiday("Sep 22 - Oct 1st","Dashain", "Celebration of good over evil", Holiday.Type.FESTIVAL),
//                new Holiday("Oct 18 - Oct 23","Tihar", "Festival of lights, celebrating the goddess Laxmi", Holiday.Type.FESTIVAL),
//                new Holiday("Mar 14","Holi", "Festival of colors, celebrating the arrival of spring", Holiday.Type.FESTIVAL),
//                new Holiday("May 12","Buddha Jayanti", "Celebrates the birth of Lord Buddha", Holiday.Type.FESTIVAL),
//                new Holiday("Oct 27","Chhath", "Festival dedicated to the sun god", Holiday.Type.FESTIVAL),
//                new Holiday("May 28", "Republic Day", "Celebration of the establishment of the republic", Holiday.Type.FEDERAL),
//                new Holiday("April 24", "Democracy Day", "Celebration of the restoration of democracy", Holiday.Type.FEDERAL),
//                new Holiday("Sept 20", "Constitution Day", "Celebration of the adoption of the constitution", Holiday.Type.FEDERAL),
//                new Holiday("March 8", "International Women's Day", "Celebration of women's achievements and rights", Holiday.Type.FEDERAL)
//        );

        Iterable<Holiday> holidays = holidaysRepository.findAll();
        // converting iterable to list
        List<Holiday> holidayList = StreamSupport.stream(holidays.spliterator(), false).collect(Collectors.toList());

        Holiday.Type[] types = Holiday.Type.values();
        for (Holiday.Type type : types) {
            model.addAttribute(type.toString(),
                    holidayList.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList()));
        }
        return "holidays.html";
    }
}

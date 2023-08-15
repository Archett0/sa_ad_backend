package sg.edu.nus.ad_backend.util;

import sg.edu.nus.ad_backend.model.Member;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AgeGrouper {
    public static List<Integer> group(List<Member> members) {
        List<Integer> ageGroupCounts = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0));
        LocalDate currentDate = LocalDate.now();
        for (Member m : members) {
            if (m.getBirthday() == null) {
                continue;
            }
            Period ageP = Period.between(m.getBirthday(), currentDate);
            int age = ageP.getYears();
            if (age < 18) {
                ageGroupCounts.set(0, ageGroupCounts.get(0) + 1);
            } else if (age >= 19 && age <= 25) {
                ageGroupCounts.set(1, ageGroupCounts.get(1) + 1);
            } else if (age >= 26 && age <= 35) {
                ageGroupCounts.set(2, ageGroupCounts.get(2) + 1);
            } else if (age >= 36 && age <= 45) {
                ageGroupCounts.set(3, ageGroupCounts.get(3) + 1);
            } else if (age >= 46 && age <= 55) {
                ageGroupCounts.set(4, ageGroupCounts.get(4) + 1);
            } else if (age >= 56 && age <= 65) {
                ageGroupCounts.set(5, ageGroupCounts.get(5) + 1);
            } else {
                ageGroupCounts.set(6, ageGroupCounts.get(6) + 1);
            }
        }
        return ageGroupCounts;
    }
}

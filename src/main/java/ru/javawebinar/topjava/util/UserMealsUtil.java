package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );

        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);

//      .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field

        Map <LocalDate,Integer>map=new TreeMap<>();
        List<UserMealWithExceed>result=new ArrayList<>();
        int sum=mealList.get(0).getCalories();
        for (int i = 1; i < mealList.size(); i++) {
            if (mealList.get(i).getDateTime().toLocalDate().equals(mealList.get(i-1).getDateTime().toLocalDate()))
                sum+=mealList.get(i).getCalories();
            if(!mealList.get(i).getDateTime().toLocalDate().equals(mealList.get(i-1).getDateTime().toLocalDate())
                    ||i==mealList.size()-1) {
                if(sum>caloriesPerDay){
                    if(mealList.get(i-1).getDateTime().toLocalTime().isAfter(startTime)&&
                            mealList.get(1-1).getDateTime().toLocalTime().isBefore(endTime)){
                        result.add(new UserMealWithExceed(mealList.get(i-1).getDateTime(),mealList.get(i-1).getDescription(),
                                mealList.get(i-1).getCalories(),true));}
                    for (int j = i-1; j >0 ; j--) {
                        if(mealList.get(j).getDateTime().toLocalDate().equals(mealList.get(j-1).getDateTime().toLocalDate())){
                            if(mealList.get(j-1).getDateTime().toLocalTime().isAfter(startTime)&&
                                    mealList.get(j-1).getDateTime().toLocalTime().isBefore(endTime)){
                            result.add(new UserMealWithExceed(mealList.get(j-1).getDateTime(),mealList.get(j-1).getDescription(),
                                    mealList.get(j-1).getCalories(),true));}
                        }else break;
                    }
                }else {
                    if(mealList.get(i-1).getDateTime().toLocalTime().isAfter(startTime)&&
                            mealList.get(1-1).getDateTime().toLocalTime().isBefore(endTime)){
                        result.add(new UserMealWithExceed(mealList.get(i-1).getDateTime(),mealList.get(i-1).getDescription(),
                                mealList.get(i-1).getCalories(),false));}
                    for (int j = i-1; j >0 ; j--) {
                        if(mealList.get(j).getDateTime().toLocalDate().equals(mealList.get(j-1).getDateTime().toLocalDate())){
                            if(mealList.get(j-1).getDateTime().toLocalTime().isAfter(startTime)&&
                                    mealList.get(j-1).getDateTime().toLocalTime().isBefore(endTime)){
                                result.add(new UserMealWithExceed(mealList.get(j-1).getDateTime(),mealList.get(j-1).getDescription(),
                                        mealList.get(j-1).getCalories(),false));}
                        }else break;
                    }
                }

                sum=mealList.get(i).getCalories();
            }
        }


        return result;
    }
}

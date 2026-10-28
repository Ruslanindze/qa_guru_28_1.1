package qa.guru.data;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(setterPrefix = "with")
public class UserDto {
    String firstName;
    String lastName;
    String email;
    String gender;
    String phoneNumber;

    String birthYear;
    String birthMonth;
    String birthDay;

    List<String> subjects;
    List<String> hobbies;

    String picture;
    String currentAddress;
    String state;
    String city;

    String permanentAddress;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getStateAndCity() {
        return state + " " + city;
    }
}

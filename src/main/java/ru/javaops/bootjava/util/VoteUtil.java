package ru.javaops.bootjava.util;

import lombok.experimental.UtilityClass;
import ru.javaops.bootjava.error.IllegalRequestDataException;
import ru.javaops.bootjava.model.User;
import ru.javaops.bootjava.model.Vote;
import ru.javaops.bootjava.web.AuthUser;

import java.time.LocalTime;
import java.util.Objects;

@UtilityClass
public class VoteUtil {
    public static void checkUser(AuthUser authUser, Vote vote, String msg) {
        User user = authUser.getUser();
        if (!Objects.equals(vote.getUser().id(), user.getId())) {
            throw new IllegalRequestDataException(msg);
        }
    }

    public static void checkTime(CharSequence voteBefore) {
        LocalTime localTime = LocalTime.now();
        LocalTime time2 = LocalTime.parse(voteBefore);
        int value = localTime.compareTo(time2);
        if (value >= 0) {
            throw new IllegalRequestDataException("You can't change your vote after 11:00");
        }
    }
}

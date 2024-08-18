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
    public static void checkUser(AuthUser authUser, Vote vote) {
        User user = authUser.getUser();
        if (!Objects.equals(vote.getUser().id(), user.getId())) {
            throw new IllegalRequestDataException("You are not allowed to delete this vote");
        }
    }

    public static void checkTime(CharSequence voteBefore) {
        LocalTime boundaryTime = LocalTime.parse(voteBefore);
        if (!LocalTime.now().isBefore(boundaryTime)) {
            throw new IllegalRequestDataException("You can't vote after " + voteBefore);
        }
    }
}

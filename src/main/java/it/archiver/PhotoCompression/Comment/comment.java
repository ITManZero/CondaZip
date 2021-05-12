package it.archiver.PhotoCompression.Comment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface comment {
    int id();
    String name() default "No body";
    String Edit() default "null";
    String date() default "00/00/0000";
}

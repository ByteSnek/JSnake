package xyz.snaker.jsnake.system;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.function.Supplier;

/**
 * Created by SnakerBone on 20/08/24
 **/
@Retention(RetentionPolicy.RUNTIME)
public @interface ErrorProne
{
    Reason value();

    enum Reason
    {
        UNSPECIFIED("Reason Not Specified", () -> null),
        UNCHECKED_JNI_ERROR("Java Native Interface Error", UnsatisfiedLinkError::new),
        UNDEFINED_BEHAVIOUR("Invocation Of Undefined Behaviour", UnknownError::new);

        private final String desc;
        private final Throwable throwable;

        Reason(String desc, Supplier<Throwable> throwable)
        {
            this.desc = desc;
            this.throwable = throwable.get();
        }

        public String desc()
        {
            return desc;
        }

        public Throwable throwable()
        {
            return throwable;
        }

        @Override
        public String toString()
        {
            return String.format("%s: %s (%s)", name(), desc(), throwable());
        }
    }
}

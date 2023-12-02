package bytesnek.hiss.system;

import java.util.Map;

/**
 * Created by SnakerBone on 1/12/2023
 **/
public interface DynamicObserver<T>
{
    Map<String, Class<? extends T>> libs();
}

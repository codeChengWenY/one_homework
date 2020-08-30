package cn.coder.anno;

import java.lang.annotation.*;

/**
 * @ClassName Service
 * @Description:
 * @Author CoderCheng
 * @Date 2020-08-28 15:45
 * @Version V1.0
 **/
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {

    String value() default "";
}

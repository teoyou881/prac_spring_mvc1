package prac_spring_mvc1.demo.domain.message;

import java.util.Locale;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

@SpringBootTest
public class MessageSourceTest {
    @Autowired
    MessageSource ms;
    
    @BeforeAll
    public static void setLocale() {
        Locale.setDefault(Locale.CANADA);
    }
    
    @Test
    void helloMessage() throws Exception {
        // if local is null, it will use default locale.
        String result = ms.getMessage("hello", null, null);
        Assertions.assertThat(result).isEqualTo("hello");
    }
    
    @Test
    void notFoundMessageCode() throws Exception {
        Assertions.assertThatThrownBy(() -> ms.getMessage("no_code", null, null))
                  .isInstanceOf(NoSuchMessageException.class);
    }
    
    @Test
    void notFoundMessageCodeDefaultMessage() throws Exception {
        // if you can't find code, defaultMessage will be used.
        String result = ms.getMessage("no_code", null, "default message", null);
        Assertions.assertThat(result).isEqualTo("default message");
        
    }
    
    @Test
    void argumentMessage() {
        // when you pass arguments, args should be an Object array
        String result = ms.getMessage("hello.name", new Object[]{"Spring"}, null);
        Assertions.assertThat(result).isEqualTo("hello Spring");
    }
    
    @Test
    void defaultLang() {
        System.out.println(Locale.getDefault());
        Assertions.assertThat(ms.getMessage("hello", null, null)).isEqualTo("hello");
        Assertions.assertThat(ms.getMessage("hello", null, Locale.ENGLISH)).isEqualTo("hello");
    }
    
    @Test
    void enKorea() {
        Assertions.assertThat(ms.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕");
        Assertions.assertThat(ms.getMessage("hello", null, Locale.KOREAN)).isEqualTo("안녕");
    }
}

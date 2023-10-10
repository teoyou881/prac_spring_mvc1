package validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

public class MessageCodesResolverTest {
    
    MessageCodesResolver CodesResolver = new DefaultMessageCodesResolver();
    
    @Test
    void messageCodesResolverObject() throws Exception {
        //given
        String[] messageCodes = CodesResolver.resolveMessageCodes("required", "item");
        for (String messageCode : messageCodes) {
            System.out.println("messageCode = " + messageCode);
        }
        
        Assertions.assertThat(messageCodes).containsExactly("required.item", "required");
        
    }
    
    @Test
    void messageCodesResolverField() throws Exception {
        //given
        String[] messageCodes = CodesResolver.resolveMessageCodes(
            "required", "item", "itemName", String.class
        );
        for (String messageCode : messageCodes) {
            System.out.println("messageCode = " + messageCode);
        }
        Assertions.assertThat(messageCodes).containsExactly(
            "required.item.itemName",
            "required.itemName",
            "required.java.lang.String",
            "required");
    }
}

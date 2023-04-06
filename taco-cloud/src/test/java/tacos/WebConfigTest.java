package tacos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import tacos.web.WebConfig;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
public class WebConfigTest {

    @Autowired
    private WebConfig webConfig;

    @Test
    public void testWebConfig() {
        assertNotNull(webConfig);
        // 这里可以添加更多测试代码，以确保 WebConfig 类中的配置正确

    }

}
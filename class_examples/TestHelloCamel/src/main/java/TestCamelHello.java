import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;


public class TestCamelHello {

    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();

        context.addRoutes(
                new RouteBuilder() {
                    public  void configure(){
                        System.out.println("Before");
                        from("file: resources/input?noop=true")
                                .to("file: resources/output");
                        System.out.println("After");
                    }
                }
        );

        context.start();
        Thread.sleep(10000);
        context.stop();


    }
}

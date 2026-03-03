package fitnessApp.ActivityMicroservice;

import org.springframework.stereotype.Component;

@Component
public class MongoDebug {

    public MongoDebug(org.springframework.context.ApplicationContext ctx) {
        String[] beans = ctx.getBeanNamesForType(com.mongodb.client.MongoClient.class);
        System.out.println("MongoClient beans:");
        for (String bean : beans) {
            System.out.println(" - " + bean);
        }
    }
}


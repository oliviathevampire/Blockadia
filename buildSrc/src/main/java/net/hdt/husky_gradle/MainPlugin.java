package net.hdt.husky_gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class MainPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getTasks().create("cheese", Greeting.class, (task) -> {
            task.setMessage("Cheese");
            task.setRecipient("is nice");
        }).setDescription("This is just a test command");

        project.getTasks().create("cheese2", Greeting.class, (task) -> {
            task.setMessage("Cheese burger");
            task.setRecipient("is nice");
        }).setDescription("This is just a test command");
    }

}
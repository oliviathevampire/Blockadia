package net.hdt.husky_gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class MainPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getTasks().create("hello", Greeting.class, (task) -> {
            task.setMessage("Hello");
            task.setRecipient("World");
        }).setDescription("This is just a test command");
    }

}
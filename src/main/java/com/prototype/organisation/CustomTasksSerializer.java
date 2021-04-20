package com.prototype.organisation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.prototype.organisation.task.Task;

@SuppressWarnings("serial")
public class CustomTasksSerializer extends StdSerializer<Collection<Task>>{
	public CustomTasksSerializer() {
        this(null);
    }

    public CustomTasksSerializer(Class<Collection<Task>> t) {
        super(t);
    }
    
    @Override
    public void serialize(
	Collection<Task> tasks, 
	JsonGenerator generator, 
	SerializerProvider provider) 
	throws IOException, JsonProcessingException {
		List<Integer> ids = new ArrayList<>();
		for (Task task : tasks) {
		    ids.add(task.getId());
		}
		generator.writeObject(ids);
	}
}

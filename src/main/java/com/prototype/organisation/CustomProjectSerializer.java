package com.prototype.organisation;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.prototype.organisation.project.Project;

@SuppressWarnings("serial")
public class CustomProjectSerializer extends StdSerializer<Project>{
	public CustomProjectSerializer() {
        this(null);
    }

    public CustomProjectSerializer(Class<Project> t) {
        super(t);
    }

    @Override
    public void serialize(
    		Project project, 
	JsonGenerator generator, 
	SerializerProvider provider) 
	throws IOException, JsonProcessingException {
		generator.writeObject(project.getId());
	}
}

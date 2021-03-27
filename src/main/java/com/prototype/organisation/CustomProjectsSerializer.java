package com.prototype.organisation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.prototype.organisation.project.Project;

@SuppressWarnings("serial")
public class CustomProjectsSerializer extends StdSerializer<Collection<Project>>{

	public CustomProjectsSerializer() {
	        this(null);
	    }

	    public CustomProjectsSerializer(Class<Collection<Project>> t) {
	        super(t);
	    }

	    @Override
	    public void serialize(
	    		Collection<Project> projects, 
	    		JsonGenerator generator, 
	    		SerializerProvider provider) 
	    		throws IOException, JsonProcessingException {
					List<Long> ids = new ArrayList<>();
					for (Project project : projects) {
					    ids.add(project.getId());
					}
					generator.writeObject(ids);
	    		}
	}

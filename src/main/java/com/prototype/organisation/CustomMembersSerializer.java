package com.prototype.organisation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.prototype.organisation.member.Member;

@SuppressWarnings("serial")
public class CustomMembersSerializer extends StdSerializer<Collection<Member>>{

	public CustomMembersSerializer() {
        this(null);
    }

    public CustomMembersSerializer(Class<Collection<Member>> t) {
        super(t);
    }

    @Override
    public void serialize(
	Collection<Member> members, 
	JsonGenerator generator, 
	SerializerProvider provider) 
	throws IOException, JsonProcessingException {
		List<UUID> ids = new ArrayList<>();
		for (Member member : members) {
		    ids.add(member.getId());
		}
		generator.writeObject(ids);
	}
}
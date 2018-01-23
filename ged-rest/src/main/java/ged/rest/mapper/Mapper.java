package ged.rest.mapper;

public interface Mapper<I, O> {

	I toObject(O object);
}
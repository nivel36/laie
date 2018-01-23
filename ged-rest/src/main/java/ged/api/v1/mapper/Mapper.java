package ged.api.v1.mapper;

public interface Mapper<I, O> {

	I toObject(O object);
}
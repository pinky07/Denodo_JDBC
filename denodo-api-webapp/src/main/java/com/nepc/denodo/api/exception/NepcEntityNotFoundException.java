package com.nepc.denodo.api.exception;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Exception to use when an Entity is not found in the database.
 *
 * @author Minor Madrigal
 */
public class NepcEntityNotFoundException extends NepcDenodoException
{

	/**
	 * Creates a new exception
	 *
	 * @param clazz           Class not found
	 * @param searchParamsMap Search parameters - Optional
	 */
	public NepcEntityNotFoundException(Class clazz, String... searchParamsMap)
	{
		super(NepcEntityNotFoundException
				.generateMessage(clazz.getSimpleName(), toMap(String.class, String.class, searchParamsMap)));
	}

	/**
	 * Creates an String message
	 *
	 * @param entity       Name of the entity
	 * @param searchParams Map of search parameters
	 * @return Exception message
	 */
	private static String generateMessage(String entity, Map<String, String> searchParams)
	{
		return StringUtils.capitalize(entity) + " was not found for parameters " + searchParams;
	}

	/**
	 * Ensures the length of the entries is even, then creates a new map in a way in which every even number is a key
	 * and the next odd number is its value.
	 *
	 * @param keyType   Key class
	 * @param valueType Value class
	 * @param entries   entries
	 * @param <K>       Key class
	 * @param <V>       Value class
	 * @return A new Map containing the entries
	 * @throws IllegalArgumentException If the number of entries is not even
	 */
	private static <K, V> Map<K, V> toMap(Class<K> keyType, Class<V> valueType, Object entries[])
	{
		if (entries.length % 2 == 1) throw new IllegalArgumentException("Number of entries must be an even number");
		return IntStream.range(0, entries.length / 2).map(i -> i * 2).collect(HashMap::new, (m, i) -> m
				.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])), Map::putAll);
	}
}

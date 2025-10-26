// when the device ID is even, send value + 1; when odd, send value * 2
share(0) { field ->
    field.map { id, value ->
        if (id % 2 == 0) value + 1 else value * 2
    }
}
@Override
    public List<Map<String, Object>> findAllBasicInfo() {
        return classeRepository.findAll().stream()
            .map(classe -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", classe.getId());
                map.put("name", classe.getName());
                return map;
            })
            .collect(Collectors.toList());
    }
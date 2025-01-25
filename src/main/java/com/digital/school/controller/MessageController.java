@GetMapping("/recipients")
@ResponseBody
public List<Map<String, Object>> getRecipients() {
    return userService.findAll(PageRequest.of(0, 1000)).stream()
        .map(user -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", user.getId());
            map.put("firstName", user.getFirstName());
            map.put("lastName", user.getLastName());
            map.put("email", user.getEmail());
            return map;
        })
        .collect(Collectors.toList());
}
@Entity
@Table(name = "levels")
public class Level {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String code;
    
    @Column(nullable = false)
    private String name;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LevelName levelName;
    
    @Column(nullable = false)
    private String cycle;
    
    @Column(name = "\"order\"", nullable = false)
    private Integer order;
    
    @OneToMany(mappedBy = "level")
    private Set<Classe> classes = new HashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "level_subjects",
        joinColumns = @JoinColumn(name = "level_id"),
        inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> subjects = new HashSet<>();

    // Getters and setters...
}
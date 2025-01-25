async function loadStudentDashboard() {
    try {
        const response = await fetch('/student/dashboard/stats');
        const data = await response.json();

        // Graphique de progrès personnel
        new Chart(document.getElementById('personalProgressChart'), {
            type: 'line',
            data: {
                labels: data.personalProgress.labels,
                datasets: [{
                    label: 'Moyenne générale',
                    data: data.personalProgress.averages,
                    borderColor: '#4C51BF',
                    tension: 0.1
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true,
                        max: 20
                    }
                }
            }
        });

        // Graphique des devoirs à venir
        new Chart(document.getElementById('upcomingHomeworkChart'), {
            type: 'bar',
            data: {
                labels: data.upcomingHomework.labels,
                datasets: [{
                    label: 'Devoirs',
                    data: data.upcomingHomework.counts,
                    backgroundColor: '#4299E1'
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true,
                        stepSize: 1
                    }
                }
            }
        });

        // Graphique de participation en classe
        new Chart(document.getElementById('classParticipationChart'), {
            type: 'radar',
            data: {
                labels: ['Présence', 'Participation', 'Devoirs rendus', 'Questions posées', 'Travail en groupe'],
                datasets: [{
                    label: 'Performance',
                    data: data.classParticipation,
                    backgroundColor: 'rgba(76, 81, 191, 0.2)',
                    borderColor: '#4C51BF'
                }]
            },
            options: {
                responsive: true,
                scales: {
                    r: {
                        beginAtZero: true,
                        max: 100
                    }
                }
            }
        });

        // Graphique des notes par matière
        new Chart(document.getElementById('subjectGradesChart'), {
            type: 'bar',
            data: {
                labels: data.subjectGrades.labels,
                datasets: [{
                    label: 'Notes',
                    data: data.subjectGrades.grades,
                    backgroundColor: '#48BB78'
                }]
            },
            options: {
                indexAxis: 'y',
                responsive: true,
                scales: {
                    x: {
                        beginAtZero: true,
                        max: 20
                    }
                }
            }
        });

        // Graphique du temps d'étude
        new Chart(document.getElementById('studyTimeChart'), {
            type: 'doughnut',
            data: {
                labels: data.studyTime.labels,
                datasets: [{
                    data: data.studyTime.hours,
                    backgroundColor: [
                        '#4C51BF',
                        '#48BB78',
                        '#4299E1',
                        '#ED8936',
                        '#F56565'
                    ]
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        position: 'bottom'
                    }
                }
            }
        });
    } catch (error) {
        console.error('Erreur lors du chargement des données:', error);
    }
}

document.addEventListener('DOMContentLoaded', loadStudentDashboard);
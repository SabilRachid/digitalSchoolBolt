async function loadProfessorDashboard() {
    try {
        const response = await fetch('/professor/dashboard/stats');
        const data = await response.json();

        // Graphique de présence par classe
        new Chart(document.getElementById('classAttendanceChart'), {
            type: 'bar',
            data: {
                labels: data.classAttendance.labels,
                datasets: [{
                    label: 'Présents',
                    data: data.classAttendance.present,
                    backgroundColor: '#48BB78'
                }, {
                    label: 'Absents',
                    data: data.classAttendance.absent,
                    backgroundColor: '#F56565'
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true,
                        max: 100,
                        ticks: {
                            callback: function(value) {
                                return value + '%';
                            }
                        }
                    }
                }
            }
        });

        // Graphique de répartition des notes
        new Chart(document.getElementById('gradesDistributionChart'), {
            type: 'bar',
            data: {
                labels: data.gradesDistribution.labels,
                datasets: data.gradesDistribution.datasets
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });

        // Graphique d'activité des devoirs
        new Chart(document.getElementById('homeworkActivityChart'), {
            type: 'line',
            data: {
                labels: data.homeworkActivity.labels,
                datasets: [{
                    label: 'Rendus à temps',
                    data: data.homeworkActivity.onTime,
                    borderColor: '#48BB78',
                    fill: false
                }, {
                    label: 'En retard',
                    data: data.homeworkActivity.late,
                    borderColor: '#ED8936',
                    fill: false
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });

        // Graphique de performance des classes
        new Chart(document.getElementById('classPerformanceChart'), {
            type: 'bar',
            data: {
                labels: data.classPerformance.labels,
                datasets: [{
                    label: 'Moyenne de la classe',
                    data: data.classPerformance.averages,
                    backgroundColor: '#4C51BF'
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

        // Graphique de participation des élèves
        new Chart(document.getElementById('studentParticipationChart'), {
            type: 'bubble',
            data: {
                datasets: data.studentParticipation.datasets
            },
            options: {
                responsive: true,
                scales: {
                    x: {
                        title: {
                            display: true,
                            text: 'Questions posées'
                        }
                    },
                    y: {
                        title: {
                            display: true,
                            text: 'Participations'
                        }
                    }
                }
            }
        });
    } catch (error) {
        console.error('Erreur lors du chargement des données:', error);
    }
}

document.addEventListener('DOMContentLoaded', loadProfessorDashboard);
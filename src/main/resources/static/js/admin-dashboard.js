// Fonction pour charger les données et initialiser les graphiques
async function loadDashboardData() {
    try {
        const response = await fetch('/admin/dashboard/stats');
        const data = await response.json();
        
        // Graphique de répartition des utilisateurs
        new Chart(document.getElementById('userDistributionChart'), {
            type: 'doughnut',
            data: {
                labels: ['Administrateurs', 'Professeurs', 'Étudiants', 'Parents'],
                datasets: [{
                    data: data.userDistribution,
                    backgroundColor: [
                        '#4C51BF',
                        '#48BB78',
                        '#4299E1',
                        '#ED8936'
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

        // Graphique de progression des inscriptions
        new Chart(document.getElementById('registrationProgressChart'), {
            type: 'line',
            data: {
                labels: data.registrationProgress.labels,
                datasets: [{
                    label: 'Inscriptions',
                    data: data.registrationProgress.data,
                    borderColor: '#4C51BF',
                    tension: 0.1
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

        // Graphique des paiements
        new Chart(document.getElementById('paymentStatusChart'), {
            type: 'bar',
            data: {
                labels: data.paymentStatus.labels,
                datasets: [{
                    label: 'Payés',
                    data: data.paymentStatus.paid,
                    backgroundColor: '#48BB78'
                }, {
                    label: 'En attente',
                    data: data.paymentStatus.pending,
                    backgroundColor: '#ED8936'
                }]
            },
            options: {
                responsive: true,
                scales: {
                    x: {
                        stacked: true
                    },
                    y: {
                        stacked: true,
                        beginAtZero: true
                    }
                }
            }
        });

        // Graphique d'utilisation des fonctionnalités
        new Chart(document.getElementById('featureUsageChart'), {
            type: 'radar',
            data: {
                labels: data.featureUsage.labels,
                datasets: [{
                    label: 'Utilisation',
                    data: data.featureUsage.data,
                    borderColor: '#4C51BF',
                    backgroundColor: 'rgba(76, 81, 191, 0.2)'
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

        // Graphique des présences
        new Chart(document.getElementById('attendanceStatsChart'), {
            type: 'bar',
            data: {
                labels: data.attendanceStats.labels,
                datasets: [{
                    label: 'Présents',
                    data: data.attendanceStats.present,
                    backgroundColor: '#48BB78'
                }, {
                    label: 'Absents',
                    data: data.attendanceStats.absent,
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
    } catch (error) {
        console.error('Erreur lors du chargement des données:', error);
    }
}

// Charger les données au chargement de la page
document.addEventListener('DOMContentLoaded', loadDashboardData);
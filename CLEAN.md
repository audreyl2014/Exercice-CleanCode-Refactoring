# Nettoyage du code

Ce document résume les différentes étapes effectuées pour nettoyer le code fourni.

## Apprendre à connaître le code

Lors de cette étape du nettoyage du code, on cherche surtout à mieux comprendre le code. Pour se faire, on utilise
surtout les outils automatiques de l'IDE pour modifier le code. Suite à ses modifications, les tests devraient encore
passer sans devoir les modifier manuellement. On se note les changements demandant des modifications plus importantes
pour la seconde phase du nettoyage, le but ici étant surtout de prendre ses repères avec le code.

Changements apportés:

1. Formatage automatique du module au complet
2. Enlever les commentaires inutiles ou mensongers, les docstrings et le code mort
3. Donner de meilleurs noms aux attributs
    1. Renommer `eList` -> `employees`
    2. Renomer `p` -> `pendingPaychecks`
    3. Renomer `h` -> `isEmployeeTakingHolidays`
    4. `Employee.vacantion_days` -> `Employee.vacationDays`
    4. `HourlyEmployee.rate` -> `HourlyEmployee.hourlyRate`
    4. `HourlyEmployee.amount` -> `HourlyEmployee.workedHoursFor2Weeks`
    4. `SalariedEmployee.biweeklySalary` -> `SalariedEmployee.biweeklySalary`
4. Ajouter le *modifier* `final` aux attributs de la classe
5. Uniformiser l'utilisation de `this` dans la classe, ici nous avons décidé de l'omettre
6. Enlever les lignes vides en trop
7. Donner de meilleurs noms aux méthodes
    1. `processPending` -> `processPendingPaychecks`
    2. `addEmp` -> `addEmployee`
    3. `findSWE` -> `findEngineers`
    4. `findMgs` -> `findManagers`
    5. `createPendings` -> `createPendingPaychecks`
    6. `salaryRaise` -> `giveRaise`
    7. `avgPayCehck_pending` -> `getAveragePendingPaycheck`
    8. `getTotalmoney` -> `getTotalMoney`
    9. `getNumEholidays` -> `getNumberOfEmployeesInHolidays`
8. Uniformiser le nommage des attributs et des méthodes au `camelCase`
    1. `find_Vice_Presidents` -> `findVicePresidents`
    2. `find_interns` -> `findInterns`
9. Les méthodes `find*()` ont beaucoup de duplication de code
    1. Création d'une méthode privée `findByRole`
    2. Simplification de la boucle `for` avec les `stream` Java
10. Simplification de `listEmployees`
    1. Utilisation des méthodes déjà faites (`find*`)
    2. Renommer les listes pour de meilleurs noms de variables
11. Changer les *for loops* commençant à 1
    1. `for (int i = 1; i <= employees.size(); ++i)` pour `for (int i = 0; i < employees.size(); ++i)`
    2. Changer les `.get(i-1)` pour `.get(i)`
12. Dans certains cas, il est possible de changer des *for loops* sur les index pour des for
    each (`for (Paycheck p : pendingPaychecks`))
13. Simplification de `getAveragePendingPaycheck`
    1. Lancer une exception s'il n'y a pas de d'employés
    2. Utilisation de `getTotalMoney`
14. Simplification de `getTotalMoney`
    1. Utilisation des `stream` de Java
15. Simplification de `getNumberOfEmployeesInHolidays`
    1. Renommer la variable `i_int` -> `numberOfEmployeesInHolidays`
16. Nettoyage des tests
    1. Renommage des noms des tests
    2. Uniformisation des noms de tests
    3. `ANOTHER_MONTHLY_AMOUNT` -> `ANOTHER_BIWEEKLY_AMOUNT`

Modifications demandant un peu plus de travail, que nous allons réaliser pendant la phase de réusinage

1. `takeHoliday` devrait être séparée en deux, le cas `payout == true` et le case `payout == false`
2. `createPendingPaychecks`, `giveRaise`, `takeHoliday` utilisation de instanceof
3. Liste de booléens pour garder de l'information qui devrait être dans la classe `Employee`
4. Utilisation d'exceptions trop génériques
5. Utilisation de *stringly typed* code (les roles)

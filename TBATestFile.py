import tbapy

# This key should ONLY be used for this example. If using this library in your own project,
# follow the steps in the README to generate your own key.
###########
# Variable Initialization
###########
tba = tbapy.TBA('OiEqRxRvNK8BK18rLKvV94bqwRmba00G4Bu0wWdPle78xom0LETx9RmbdZZbWVZB')
myTeam = input("Enter Team Name: ")
myTeam = int(myTeam)
myYear = input("Enter Year: ")
myYear = int(myYear)
myFile = "%s-%s.csv" % (myTeam, myYear)
print(myFile)
f= open(myFile,"w+")
f.write('"OurComp","Team","Event Year","Event Name","Event Type","Event Key","Event Week"\n')
lastYear = myYear-1
lastlastYear = myYear-2
lastlastlastYear = myYear-3
# Pull team data for year in question
team = tba.team(myTeam)
# Pull event data for team and year in question
events = tba.team_events(myTeam, myYear)
print('-' * 10 + ' Team Report' + '-' * 10)
print('Team %s is from %s in %s, %s.' % (team.team_number, team.city, team.state_prov, team.country))
print('In %d, team %s is in %d events: %s.' % (myYear, team.team_number, len(events), ', '.join(event.event_code for event in events)))
for i in range (0, len(events)-1):
    print()
    eventName = events[i]['short_name']
    eventKey = events[i]['key']
    eventWeek = events[i]['week']+1
    eventDistrict = events[i]['district']['display_name']
    event_teams = tba.event_teams(eventKey,keys=True)
    myCount = len(event_teams)
    print('The %s Event (aka %s) is in week %s and has %d teams competing: ' % (eventName, eventKey, eventWeek, myCount))
    print()
# Loop through the events for this team
#    for j in range(0, myCount):
    for j in range(0, 5):
        teamKey = event_teams[j]
        thisTeam = int(event_teams[j][3:])
        thisTeamEvents = ''
        thisTeamEvents = tba.team_events(teamKey, lastYear)
        for k in range (0, len(thisTeamEvents)):
            thisEventType = thisTeamEvents[k]['event_type_string']
            if thisEventType == 'Offseason': # Exclude Offseason events
                break
            thisEventName = thisTeamEvents[k]['short_name']
            thisEventKey = thisTeamEvents[k]['key']
            thisEventWeek = thisTeamEvents[k]['week']
#            if thisTeam == 1025: # Debugging tool update for team as needed to see data
#                print(thisTeamEvents[k])
# Retreive Team awards and other team/year info (rank points etc)            
            print('%s - %s - %s - %s - %s' % (thisTeam, thisEventName, thisEventType, thisEventKey, thisEventWeek))
            f.write('"%s","%s","%s","%s","%s","%s","%s"\n' % (eventName, thisTeam, lastYear, thisEventName, thisEventType, thisEventKey, thisEventWeek))
# Retreive Match Data
            thisMatchData=''
            thisMatchData = tba.team_matches(thisTeam,thisEventKey)
            print(thisMatchData)
# Write out our records, should we have two table?  One for header and one for match details?            
print()





# close the file
f.close() 

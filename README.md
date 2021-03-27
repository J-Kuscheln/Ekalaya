# Ekalaya
Web App for Ekalaya Organisation

by: Rozaan Wiryanto Handoyo

date: 27/03/2021
This is the "late" first commit (Sorry for that). 
The current condition will be described in details below.

Currently running features:
- Login/Logout
- Sign up
- Create new project
- Relate User as leader of a project
- Relate User as member of a project


In progress:
- Edit profile

Should be added:
- Edit Project
- relate Project with Milestones
- Messages section
  - Ability for leader of project to give tasks to member(s)
  - Ability for member to accept or reject the task
  - Ability for member to "done", "delay", and "give up" the accepted task
- Home Page
- Chat room
- "play room"

Bugs:
- Default Spring session cookie expired by closing the browser --> User automatically logged out from session.
    --> Possible solution: create custom cookie with maxAge.
    
Possible improvement:
- UI/Design
- Animations
- Header transition every time load new page
- Many more

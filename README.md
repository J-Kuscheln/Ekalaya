# Ekalaya
Web App for Ekalaya Organisation

by: Rozaan Wiryanto Handoyo

first commit date: 27/03/2021
This is the "late" first commit (Sorry for that). 

The current condition will be described in details below.

Currently running features:
- Login/Logout
- Sign up
- Create new project
- Relate User as leader of a project
- Relate User as member of a project
- Remove project
- Edit profile
- Remove Account


In progress:
- Edit Project
  - update functionality
  - autofill all text-field
- Project details
  -Details Page
  -Member can only read
  -Leader Automatically to Edit Page

Should be added:
- "Take Leadership" from project with no leader
- Add new entity to contain projects with no leader
- Make scheduler to remove projects with no leader
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
- Apply Spring Security
- Many more

# iAmHungry App for Android

Android repository for the App to display places around when you are hungry



https://user-images.githubusercontent.com/903097/128585876-461fa7a3-f623-42a4-a470-cb25f9c45bc9.mp4



## Getting Started

### Get Information

The project uses the FourSquare API to display venues around you.


### Get The Code

To clone the repository, run:
```
$ cd <your directory for source code>/
$ git clone git@github.com:kikoso/iAmHungry.git
$ Open the root folder on Android Studio
$ Add your FourSquare client key, Foursquare Secret and Google Maps API Key to local.properties
```

This project aims to use the latest stable version of Android Studio (4.2.2 at 18.07.2021).

## Project Structure

The project is using MVVM with a Clean Code flavor. Some of the libraries used are:

- Clean with MVVM
- Libraries
	- Retrofit, OkHttp for network
	- Hilt (DI)
	- Coroutines
	- AAC viewmodel
	- Navigation
	- ViewBinding
	- Picasso

## Further work

- Modularisation into different categories.
- Persistance and support of offline mode.
- Filtering
- More testing.
- Flow

## Making Changes

This project follows the [Gitflow Workflow](https://nvie.com/posts/a-successful-git-branching-model/).

The ```main``` branch stores the official release history and the ```develop``` branch is the integration branch for the next release.

When developing a new feature or fix that should go into the next release, you should create a new branch from ```develop``` using the ```feature/``` prefix. Give the branch a name that includes the Jira identifier and a short description:


```
$ git checkout develop
$ git checkout -b feature/IAMHUNGRY-1-setup-initial-project-and-repo
```

When the work is completed, push the latest changes and create a [pull request](https://github.com/kikoso/iAmHungry/pulls) to merge the changes into ```develop```. Once the PR has been approved, we prefer to squash merge the changes to keep the commit history clean. You can do this from the GitHub interface when merging the PR or manually:

```
$ git checkout develop
$ git merge --squash feature/IAMHUNGRY-1-setup-initial-project-and-repo
```

The final commit message should start with a description of the Jira item followed by a description of what was done:

```
IAMHUNGRY-1: Set up initial Android project and repo

- Update README with initial info on project.
```

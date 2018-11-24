# aoc-2018

Code and Development environment for adventofcode.com - 2018 edition

In this repository, we will collect the code for the solutions of [aventofcode](https://adventofcode.com) for the 2018 event.

If you want to join the discussions, here is the invitation for our [slack channel](https://join.slack.com/t/aoc-2018/shared_invite/enQtNDg2NTI4NzY0Mjg5LTMzMDI1NzIyM2JiMzRhNGJhZTIwMWE4Y2Q3NmZmZjRlNWFhZDAwOWFkZDc0M2QxYTYzOGFmN2ZlZjIyYjNlZTU).

The slack channel itself is [aoc-2018](https://aoc-2018.slack.com/)

The id of the [shared leaderboard](https://adventofcode.com/2018/leaderboard/private/view/117454) is `117454-7d5aa225` .

## The Goal of this repository

... is to 

- have a shared code base. It weill be interesting to see how the same problem is solved in different languages with different approaches
- have a development environment in which all examples run out of the box

### How do we ensure that all examples run out of the box?

The best solution will be to use https://gitpod.io . Just prefix the URL with `gitpod.io#` or click on https://gitpod.io#https://github.com/rdmueller/aoc-2018 and a webbased IDE will open with the repository already cloned in a docker container.

It would be awesome if we manage to get all solutions up and running in this container!

## Repository structure

The structure we start with is the following:

```
.
├── README.md
├── day01
├── day02
│   └── groovy
│       ├── rdmueller
│           ├── solution.groovy
│           └── description.adoc
│   └── [language]
│       ├── [githubhandle]
│           ├── solution.[extension]
│           └── description.adoc
├── day03
├── day04
├── ...
```

## Documentation

As you can see in the above repository structure, each folder contains a `description.adoc`. 
It would be great if everybody could explain h(er|is) solution with a short description.

## Questions?

=> see you on Slack: [aoc-2018](https://aoc-2018.slack.com/)

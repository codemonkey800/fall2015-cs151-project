# Fall 2015 CS151 Project
SealTeamSix's CS151 Project!

# Reports
Submit your reports in the `reports/` directory in the corresponding week. Write it as a txt
file, and the team leader will compile the the weekly report at the end of the week. Name
the report using your name. For example, my report is named `jeremy.txt`.

Remember to follow this format:

```
Sample Individual Weekly Report
Name:  Your name goes here.

M: 10/26
T: 10/27
W: 10/28
R: 10/29
F: 10/30
A: 10/31
```

# Docs
I'll generate JavaDocs into the `docs/` folder whenever there's a merge into master. Use that
to help code stuff.

# Submitting Code
Don't submit your code into `master`. You should only merge non-code files, like `.gitignore`, `README.md`, or `build.gradle`.
When submitting code, checkout a separate branch naming the feature you want to add, commit your code, then submit a pull
request.

Say you want to add the pits `JComponent` or whatever to the app. You an create a new branch locally like so:
```bash
git checkout -b pits-jcomponent
```

Now you can write your code changes and stuff. When you're done,
commit your code like so:

```bash
git add .
git commit -m 'Some cool commit message bruh'
git push origin pits-jcomponent
```

Then if you go on the [repo](https://github.com/codemonkey800/fall2015-cs151-project/tree/mancala-game),
you'll find this thing here:

![](/images/shit1.png)

Click on the green button that says *Compare & pull request*.

Now you can write your title and description for your pull request. Maybe add a milestone or label if you feel cool.

![](/images/shit2.png)

Happy hacking team!

# Unit Tests
You don't have to write unit tests, but it'd be cool if you do. They help with ensuring code correctness. :)

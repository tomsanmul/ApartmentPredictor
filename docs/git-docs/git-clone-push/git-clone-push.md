# Git & GitHub flow

## Summary & Goal

- [Authomate Sync to Local](https://gist.github.com/AlbertProfe/4f9d89ffe14beb62d166730929070e9a)

- [Create local and remote repo from everywhere](https://gist.github.com/AlbertProfe/f2bcc130442707f489976e01a0f78fae)

- [Git commands](https://gist.github.com/AlbertProfe/b05e8198f02b311d18e7c4a86c5241e0)

- [Git: undoing changes](https://www.atlassian.com/git/tutorials/undoing-changes)

- [Git](/devops/devops-vc-git.qmd)

- https://git-scm.com/

- [GitHub CLI | Take GitHub to the command line](https://cli.github.com/manual/gh_auth_login)

## Two accounts

Authenticate with a GitHub host, using your by default browser (<mark>Firefox</mark> or Chrome).

Then we need to authenticate asecond account, for example with<mark> Brave browser</mark>. 

On most Linux distributions, the Brave Browser executable is `brave-browser` (sometimes aliased as `brave`).

### Recommended command:

```bash
BROWSER=brave-browser gh auth login
```

If `brave-browser` doesn't work (e.g., command not found), try:

```bash
BROWSER=brave gh auth login
```

### Simulated login flow with Brave

Assuming you run the command above and choose the default web-based authentication:

```bash
$ BROWSER=brave-browser gh auth login
? What account do you want to log in to? GitHub.com
? What is your preferred protocol for Git operations? [Use arrows to move, type to filter]
  > HTTPS
    SSH
? Authenticate Git with your GitHub credentials? Yes
? How would you like to authenticate GitHub CLI? [Use arrows to move, type to filter]
  > Login with a web browser
    Paste an authentication token

First copy your one-time code: ABCD-1234
Press Enter to open https://github.com/login/device in your browser...
```

- You press **Enter**.
- The `gh` CLI launches **Brave Browser** (instead of your default browser) and automatically opens the URL `https://github.com/login/device`.
- The page loads in Brave with the one-time code **already pre-filled** (ABCD-1234 in this example).
- You click **Continue**, then review and click **Authorize GitHub CLI** (or similar).
- The page confirms authorization.
- Back in the terminal, after a few seconds:

```
✓ Authentication complete.
- gh config set -h github.com git_protocol https
✓ Configured git protocol
✓ Logged in as your-github-username
```

You're now authenticated, and future `gh` commands will use your credentials.

### Optional: Open in Brave private (incognito) window

Brave supports the `--incognito` flag (like Chrome). To avoid using an existing logged-in session:

```bash
BROWSER="brave-browser --incognito" gh auth login
```

This opens the device login page in a private window, ensuring a clean GitHub session.

This works reliably as `gh` respects the `BROWSER` variable for launching the URL, and Brave handles standard Chromium command-line flags.

## Clone & push

We are doing:

- A local clone of the original project.
- An identical copy under your own account (`albertproton/BooksFrontend`).
- The ability to work independently or later contribute back via Pull Requests.

This guide assumes:

- You are on **Linux**.
- You have **git** and **gh** (GitHub CLI) installed.
- You are authenticated as **albertproton** (check with `gh auth status`).

### Step 1: Create an Empty Repository on Your albertproton Account

1. Log in to GitHub as **albertproton**.
2. Go to https://github.com/new
3. Name the repo **BooksFrontend** (or any name you prefer).
4. **Do NOT** initialize with README, .gitignore, or license (keep it empty).
5. Click **Create repository**.
   - Note the URL: `https://github.com/albertproton/BooksFrontend.git`

Or you can user the gh cli: `gh repo create BooksFrontend --public`

### Step 2: Clone the Original Project Locally

```bash
# Choose a folder for your projects
mkdir -p ~/MyProjects && cd ~/MyProjects

# Clone from AlbertProfe
git clone https://github.com/AlbertProfe/BooksFrontend.git
cd BooksFrontend
```

You now have a local copy with `origin` pointing to AlbertProfe's repo.

### Step 3: Add Your Own Repo as a Second Remote

```bash
# Add your own repo as a new remote (call it "myrepo" or "origin-personal")
git remote add origin2 https://github.com/albertproton/BooksFrontend.git

# Verify
git remote -v
```

You should see:

```
origin  https://github.com/AlbertProfe/BooksFrontend.git (fetch/push)
myrepo  https://github.com/albertproton/BooksFrontend.git (fetch/push)
```

### Step 4: Push the Entire History to Your Repo

```bash
# Push all branches and tags to your repo
git push origin2 --all     # Pushes all branches (mainly master/main)
git push origin2 --tags    # Pushes tags (if any exist)

# Optional: Set your repo as the default upstream for easier future pushes
git push -u myrepo master   # or main, depending on default branch
```

Since you own the `albertproton/BooksFrontend` repo and are authenticated as `albertproton`, this will succeed without permission errors.

### Step 5: (Recommended) Clean Up or Adjust Default Remote

If you want future `git push`/`git pull` to go to **your** repo by default:

```bash
git remote rename origin upstream      # Keep AlbertProfe as "upstream" for pulling updates
git remote rename origin2 origin        # Your repo becomes "origin"
```

Now:

- `git pull` → gets latest from your repo.
- `git pull upstream master` → gets updates from AlbertProfe if needed.

## Example for Sharing a Remote Repository on GitHub with Two Users (AlbertProfe and AlbertProton)

> For a small project with just **two people**, the goal is to have a shared remote repository where both can push/pull changes, collaborate on code, and manage the project effectively. GitHub provides several straightforward ways to achieve this. I'll outline the main options, with pros/cons, and recommend the best for teaching beginners.

#### 1. **Shared Repository Model (Recommended for Beginners and Small Teams)**

- One user (e.g., **AlbertProfe**) creates the repository under their personal account.

- AlbertProfe invites **AlbertProton** as a **collaborator** (full write access: push directly to branches, create PRs, etc.).

- Both clone the same remote repo and work on it (using branches for features).
  
  **Steps:**
1. AlbertProfe creates the repo on GitHub (e.g., `https://github.com/AlbertProfe/my-project`).

2. Go to repo → **Settings** → **Collaborators** (under Access) → Add AlbertProton by username.

3. AlbertProton accepts the invitation via email or GitHub notifications.

4. Both users clone: `git clone https://github.com/AlbertProfe/my-project.git`

5. Work on feature branches: `git checkout -b my-feature`, commit, `git push origin my-feature`

6. Merge via Pull Requests (PRs) for review, or push directly to `main` if trusted.
   
   **Pros:**
- Simple and direct—no extra copies of the repo.

- Easy syncing: Both push/pull from the same remote.

- Great for teaching core Git concepts (branches, commits, merges, PRs).

- Collaborators can manage issues, PRs, and even push to `main` if needed.
  
  **Cons:**

- The owner (AlbertProfe) has ultimate control (can remove collaborators).

- Direct pushes possible, so teach using branches + PRs to avoid conflicts/overwrites.
  
  This is the most common and beginner-friendly way for small teams.

#### 2. **Fork and Pull Request Model**

- One user (e.g., AlbertProfe) creates the "upstream" repo.

- The other (AlbertProton) **forks** it to their account (creates a personal copy).

- AlbertProton works on their fork, pushes changes there, then opens **Pull Requests** to the upstream repo.

- AlbertProfe reviews and merges PRs.
  
  **Steps:**
1. AlbertProfe creates the repo.

2. AlbertProton goes to the repo page → Click **Fork** → Creates `https://github.com/AlbertProton/my-project`.

3. AlbertProton clones their fork, adds upstream remote: `git remote add upstream https://github.com/AlbertProfe/my-project.git`

4. Work on branches in the fork, push to fork, open PR to upstream.

5. Sync changes: `git fetch upstream` + merge regularly.
   
   **Pros:**
- Safer for open-source or untrusted contributors (no direct push access).

- Forces PR workflow—excellent for teaching code review.

- Each has their own space to experiment.
  
  **Cons:**

- More complex (multiple remotes, syncing upstream).

- Extra repo copies can confuse beginners.

- Less ideal for very small teams where trust is high.
  
  Use this if you want to emphasize PRs and review from day one.

#### 3. **GitHub Organization (Best for Long-Term or Growing Teams)**

- Create a free **GitHub Organization** (e.g., "AlbertTeam").

- Add both accounts as owners/members.

- Create the repo under the organization.

- Both have equal access (no single "owner" bottleneck).
  
  **Steps:**
1. One user creates the org (github.com/organizations → New organization → Free plan).

2. Invite the other user → They accept.

3. Create repo under the org.

4. Both clone and work (same as shared model).
   
   **Pros:**
- Neutral ownership—neither account "owns" the repo.

- Scales easily if more students join.

- Teams, permissions, and projects features available.
  
  **Cons:**

- Slight overhead to set up org.

- Overkill for exactly 2 people initially.
  
  Great if this is part of a class with potential for more collaborators.

#### Best Practices to Teach (Regardless of Option)

- Always work on **feature branches** (never directly on `main`).
- Commit often with clear messages.
- Pull frequently: `git pull origin main` before starting work.
- Use **Pull Requests** for merging—even in shared repo—to practice review.
- Resolve conflicts collaboratively (Git handles them well).
- Protect the `main` branch (Settings → Branches → Add rule: Require PR reviews) to enforce good habits.



This mirrors standard industry practices for small teams! Let me know if you need step-by-step commands or diagrams.

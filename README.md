<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*

  - [Installation](#installation)
  - [Usage](#usage)
    - [Adding toc to all files in a directory and sub directories](#adding-toc-to-all-files-in-a-directory-and-sub-directories)
    - [Update existing doctoc TOCs effortlessly](#update-existing-doctoc-tocs-effortlessly)
    - [Adding toc to individual files](#adding-toc-to-individual-files)
- [Section One](#section-one)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Installation

    npm install -g doctoc

## Usage

In its simplest usage, you can pass one or more files or folders to the
`doctoc` command. This will update the TOCs of each file specified as well as of
each markdown file found by recursively searching each folder. Below are some
examples.

### Adding toc to all files in a directory and sub directories

Go into the directory that contains you local git project and type:
    
    doctoc .

This will update all markdown files in the current directory and all its
subdirectories with a table of content that will point at the anchors generated
by the markdown parser. Doctoc defaults to using the GitHub parser, but other
[modes can be
specified](#using-doctoc-to-generate-links-compatible-with-other-sites).


### Update existing doctoc TOCs effortlessly

### Adding toc to individual files


# Section One

Here we'll discuss...

```

Running doctoc will insert the toc at that location.

### Specifying a custom TOC title

Use the `--title` option to specify a (Markdown-formatted) custom TOC title; e.g., `doctoc --title '**Contents**' .` From then on, you can simply run `doctoc <file>` and doctoc will will keep the title you specified.

Alternatively, to blank out the title, use the `--notitle` option. This will simply remove the title from the TOC.

### Specifying a maximum heading level for TOC entries

Use the `--maxlevel` option to limit TOC entries to headings only up to the specified level; e.g., `doctoc --maxlevel 3 .`

By default,

- no limit is placed on Markdown-formatted headings,
- whereas headings from embedded HTML are limited to 4 levels.

### Printing to stdout

You can print to stdout by using the `-s` or `--stdout` option.

[ack]: http://beyondgrep.com/

### Only update existing ToC

Use `--update-only` or `-u` to only update the existing ToC. That is, the Markdown files without ToC will be left untouched. It is good if you want to use `doctoc` with `lint-staged`.

### Usage as a `git` hook

doctoc can be used as a [pre-commit](http://pre-commit.com) hook by using the
following configuration:

```yaml
repos:
-   repo: https://github.com/thlorenz/doctoc
    rev: ...  # substitute a tagged version
    hooks:
    -   id: doctoc
```

This will run `doctoc` against markdown files when committing to ensure the
TOC stays up-to-date.

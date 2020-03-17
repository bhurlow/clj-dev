

add the following to `~/.clojure/deps.edn`

```edn
{:aliases
 {:dev {:extra-deps {bh {:git/url "git@github.com:bhurlow/clj-dev.git"
                         :sha "ed53a76d208fd85980c6b0f24e4de293cbb0bf21"}}
        :main-opts ["-m" "bh.main"]}}}
```

then start repl with:

```shell
clj -A:dev
```

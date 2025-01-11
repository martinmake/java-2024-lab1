(define-module (java-2024-lab1-package)
  #:use-module (guix)
  #:use-module (guix packages)
  #:use-module (guix utils)
  #:use-module (guix gexp)
  #:use-module (guix git-download)
  #:use-module (guix build-system maven)
  #:use-module (guix build maven-build-system)
  #:use-module ((guix licenses) #:prefix license:)
  #:use-module (gnu packages java)
  #:use-module (gnu packages maven)
  #:use-module (gnu packages maven-parent-pom))

(define vcs-file?
  ;; Return true if the given file is under version control.
  (or (git-predicate (dirname (dirname (current-source-directory))))
      (const #t)))

(define-public java-2024-lab1
  (package
    (name "java-2024-lab1")
    (version "0.0.0")
    (source (local-file "../.." "java-2024-lab1-checkout"
                        #:recursive? #t
                        #:select? vcs-file?))
    (build-system maven-build-system)
    (arguments
     `(#:jdk ,openjdk17))

    (propagated-inputs
     (list java-commons-io
           java-commons-math3))
    (native-inputs
     (list java-junit maven-parent-pom-34))
    (home-page "https://github.com/martinmake/java-2024-lab1")
    (synopsis "...")
    (description "...")
    (license license:gpl3)))

java-2024-lab1
